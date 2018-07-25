Tiny Groovy class to mock Spring JpaRepository
===

## What's this?

I often create Spring application and write unit test with groovy.  
As for unit test, I was using [spring-data-mock](https://github.com/mmnaseri/spring-data-mock) to mock JpaRepository.
But it has some problems.  

- not supporting 'In' phrase for find* query
- not supporting SpringBoot 2.x

spring-data-mock is java library and supporting almost all features of JpaRepository.  
So its source code is so large(of course I respect the creator!).  
It's so tough to create another spring-data-mock...

But I found I can create a mocking class of JpaReposutory as just 1 short class, with power of Groovy.  
The class is **[RepoProxy](https://github.com/hitokun-s/mock-jpa-repository-test/blob/master/src/test/groovy/RepoProxy.groovy)**.

## How to use this?

    def mockRepository = new RepoProxy() as PersonRepository
    
That's all. See **[PersonSpec](https://github.com/hitokun-s/mock-jpa-repository-test/blob/master/src/test/groovy/PersonSpec.groovy)**.   

## Attention

This is an experimental code, so RepoProxy does not have all common methods of JpaRepository.  
But you can add any methods easily as you want.  
For example, if you need `exists` method for your repository class, you only have to add the method to RepoProxy.  
Like so, now RepoProxy works perfectly for my real spring projects.   

## Question & Trouble shooting

Please feel free to ask me!
