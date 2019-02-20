package com.deepak.ratingsdataservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.ratingsdataservice.model.Rating;

@RestController
@RefreshScope
@RequestMapping("/ratings")
public class RatingsDataController
{
   private static final Logger logger = LoggerFactory.getLogger( RatingsDataController.class );

   @Value("${eureka.instance.instance-id}")
   private String instanceId;
   
   @Value("${message:Ratings Data service - Config Server is not working..pelase check}")
   private String message;
   
   @RequestMapping( method=RequestMethod.GET, path="/movies/{movieId}")
   public Rating getRatingForMovie( @PathVariable("movieId") String movieId )
   {
      return new Rating( movieId, 4 );
   }
   
   @RequestMapping( method=RequestMethod.GET, path="/message")
   public String getMessage()
   {
      String message = this.message + " - with instance id - "+ this.instanceId; 
      logger.debug( "Here's the message : {}", message );
      return message;
   }
   
   @RequestMapping( method=RequestMethod.GET, path="/users/{userId}")
   public List< Rating > getRatingsForUser( @PathVariable("userId") String userId )
   {
      logger.debug( "Instance Id for ratings-data-service : {}", instanceId );
      
      return Stream.< Rating > builder()
                   .add( new Rating( "1234", 4 ) )
                   .add( new Rating( "1236", 6 ) )
                   .add( new Rating( "1237", 7 ) )
                   .build()
                   .collect( Collectors.toList() ); 
   }
}
