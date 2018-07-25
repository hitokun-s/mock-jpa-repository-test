
class RepoProxy extends groovy.util.Proxy {

  def pool = []
  def idGen = 0

  def save(entity){
    if(!entity.id){
      entity.id = ++idGen
      pool << entity
    }else{
      def idx = pool.indexOf(pool.find{it.id == entity.id})
      pool[idx] = entity
    }
    entity
  }

  def saveAll(List entities){
    entities.collect(this.&save)
  }

  def deleteAll(){
    pool = []
  }

  def findById(id){
    Optional.ofNullable(pool.find{it.id == id})
  }

  def findAllById(List ids){
    pool.findAll{it.id in ids}
  }

  def count(){
    pool.size()
  }

  Object invokeMethod(String name, Object args){

    println "invoked! name: $name, args: $args"

    def sortProp, sortOrder
    args = (List)args

    if(name.startsWith("find")){
      boolean isAll = name.startsWith("findAll")
      List<String> splited = split(name)

      println "query parsed: $splited"

      if(splited.contains("OrderBy")){
        println "found 'OrderBy'"
        def orderByIdx = splited.indexOf("OrderBy");
        sortProp = splited[orderByIdx + 1].uncapitalize()
        sortOrder = orderByIdx + 2 <= splited.size() - 1 ? splited[orderByIdx + 2].uncapitalize() : "asc"
        splited = splited[0..orderByIdx - 1]
      }

      List<Closure> conds = createConditions(splited, args)
      def res = pool
      for(Closure c:conds){
        res = res.findAll(c)
      }
      if(sortProp){
        res = res.sort {it[sortProp]}
        if(sortOrder == "desc"){
          res = res.reverse()
        }
      }
      return isAll ? res: res[0]
    }
    throw new RuntimeException("method:$name not found")
  }

  public List<Closure> createConditions(List keywords, List args){
    List<Closure> res = []

    // 先頭のfind,all,byは除く
    while(keywords[0] in ["findBy", "findAllBy", "find", "All", "By", "First"]){
      keywords = keywords.drop(1)
    }
    println "keywords: $keywords"

    Iterator<String> itr = keywords.iterator()
    Iterator<Object> argsItr = args.iterator()

    while(itr.hasNext()){
      def word = itr.next().uncapitalize()
      if(word == "and"){
        continue
      }
      def nextWord = itr.hasNext() ? itr.next(): null

      println "word: $word"
      println "nextWord: $nextWord"

      def arg = argsItr.next()
      println "arg: $arg"
      if(nextWord && nextWord in ["GreaterThanEqual", "GreaterThan", "LessThanEqual", "LessThan", "In"]){
        Closure c
        switch(nextWord){
          case "GreaterThanEqual": c = {
            it[word] >= arg
          }; break;
          case "GreaterThan": c = {
            it[word] > arg
          }; break;
          case "LessThanEqual": c = {
            it[word] <= arg
          }; break;
          case "LessThan": c = {
            it[word] < arg
          }; break;
          case "In": c = {
            it[word] in arg
          }; break;
        }
        res << c
      }else{
        res << {
          it[word] == arg
        }
      }
    }
    res
  }

  public List<String> split(String target){
    def specialWords = [
        "find",
        "First",
        "By",
        "All",
        "GreaterThanEqual",
        "GreaterThan",
        "LessThanEqual",
        "LessThan",
        "In",
        "And",
        "Or",
        "OrderBy",
        "Asc",
        "Desc"
    ]

    def list =  target.split("(?=[A-Z])")
    println list

    def list2 = []
    def tmp = ""
    list.eachWithIndex{ String s, int i ->
      boolean skip = false
      if(specialWords.find {it.startsWith(tmp + s)}){
        tmp += s
        skip = true
      }
      if(specialWords.contains(tmp)){
        if(i == list.size() - 1 || !specialWords.find {it.startsWith(tmp + list[i + 1])}){
          list2 << " $tmp "
          tmp = ""
        }
        skip = true
      }
      if(!skip){
        list2 << s
      }
    }
    list2.join().split()
  }
}
