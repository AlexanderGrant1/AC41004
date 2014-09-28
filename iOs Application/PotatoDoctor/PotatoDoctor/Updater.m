//
//  Updater.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "Updater.h"


@implementation Updater


- (id)init
{
    if (self) {
        // Custom initialization
        self.serverDomain = @"https://zeno.computing.dundee.ac.uk/2014-projects/team8/web/";
    }
    
    return self;
}

-(void) queryBackend
{
    // Truncate all tables.
    [[DbManager sharedManager] clearTables];
    
    // Update databases.
    [self queryBackend:PESTS];
    [self queryBackend:TUBERS];
    [self queryBackend:PLANTLEAF];
}

-(void) updatePests: (NSData *) data
{
    // Insert pest data inside the db.
    [self insertEntries:PESTS :data];

}

-(void) updatePlantLeafs: (NSData *) data
{
     // Insert plantLeaf data inside the db.
    [self insertEntries:PLANTLEAF :data];
}

-(void) updateTubers: (NSData *) data
{
     // Insert Tuber dat inside the db.
    [self insertEntries:TUBERS :data];
}

-(void) updateTutorials: (NSData *) data
{
     // Insert Tutorial data inside the db.
   // [self insertEntries:TUTORIALS :data];
}


-(void) insertEntries: (SECTIONS) section :  (NSData *) data
{
    NSString *tableName = [self sectionName:section];
    NSError *jsonParsingError = nil;
    
    NSMutableArray *responseArray = [NSJSONSerialization JSONObjectWithData:data
                                                                    options:0 error:&jsonParsingError];
    
    
    NSArray *jsonEntries = [responseArray valueForKey:@"Entries"];
    NSArray *jsonPhotos = [responseArray valueForKey:@"Photos"];
    
    NSManagedObjectContext *context = [[DbManager sharedManager] managedObjectContext];
    
    // Pre-define the objects
    Pest *pestEntry = Nil;
    Tuber *tuberEntry = Nil;
    PlantLeaf *plantLeafEntry = Nil;
    
    for(NSDictionary *jsonEntity in jsonEntries)
    {
        // Add Entry to the db.

        @try{
            
            NSString *description = [jsonEntity objectForKey:@"Description"];
            NSString *name        = [jsonEntity objectForKey:@"Name"];
            NSInteger objId       = [[jsonEntity objectForKey:@"Id"] intValue];
            

            
            
            // Pre-define the objects
            pestEntry = Nil;
            tuberEntry = Nil;
            plantLeafEntry = Nil;
            
            
            switch(section)
            {
                case PESTS:
                {
                    pestEntry = (Pest*)[NSEntityDescription
                                                insertNewObjectForEntityForName:tableName
                                                inManagedObjectContext:context];
                    
                    [pestEntry setValue:name forKey:@"name"];
                    [pestEntry setValue:description forKey:@"descriptionText"];
                    
                    
       
                    break;
                }
                case PLANTLEAF:
                {
                    plantLeafEntry = (PlantLeaf*)[NSEntityDescription
                                                insertNewObjectForEntityForName:tableName
                                                inManagedObjectContext:context];
                    
                    [plantLeafEntry setValue:name forKey:@"name"];
                    [plantLeafEntry setValue:description forKey:@"descriptionText"];
                    

                    break;
                }
                    
                case TUBERS:
                {
                    tuberEntry = (Tuber*)[NSEntityDescription
                                                insertNewObjectForEntityForName:tableName
                                                inManagedObjectContext:context];
                    
                    [tuberEntry setValue:name forKey:@"name"];
                    [tuberEntry setValue:description forKey:@"descriptionText"];
                    
       
                    break;
                }
                    
                    
            }

            
            
            // Get all photos for this entry ( really bad way, don't have time for proper implementation, sorry.. ;( )
            for(NSDictionary *jsonPhoto in jsonPhotos)
            {
                // Check whether the id matches the id of this entry.
                NSInteger entryId = [[jsonPhoto objectForKey:@"EntryId"] intValue];
                
                if(entryId == objId)
                {
                
                    // This photo belongs to this entry.
                    
                    // Add the photo to the
                    NSString *imgName = [jsonPhoto objectForKey:@"ImageName"];
                    
        
                
                    
                    // Create the linker
                    switch(section)
                    {
                        case PESTS:
                            {
                         
                                
                                // Add plant leaf photo linker.
                                Photo *photo = (Photo *)[NSEntityDescription
                                                         insertNewObjectForEntityForName:@"Photo"
                                                         inManagedObjectContext:pestEntry.managedObjectContext];
                                
                                photo.name = imgName;
                                
                               [photo addPestPhotoRelObject:pestEntry];

                              

                            break;
                            }
                        case PLANTLEAF:
                            {
                                // Add plant leaf photo linker.
                                Photo *photo = (Photo *)[NSEntityDescription
                                                         insertNewObjectForEntityForName:@"Photo"
                                                         inManagedObjectContext:plantLeafEntry.managedObjectContext];
                                
                                photo.name = imgName;
                                
                                [photo addPlantLeafRelObject:plantLeafEntry];
                                break;
                            }
                            
                        case TUBERS:
                            {
                                // Add plant leaf photo linker.
                                Photo *photo = (Photo *)[NSEntityDescription
                                                         insertNewObjectForEntityForName:@"Photo"
                                                         inManagedObjectContext:tuberEntry.managedObjectContext];
                                
                                photo.name = imgName;
                                
                                [photo addTuberPhotoRelObject:tuberEntry];
                                
                                break;
                            }
                            

                    }
                    
                    
                    // Download the image

                    NSArray       *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
                    NSString  *documentsDirectory = [paths objectAtIndex:0];
                    
                    NSString  *filePath = [NSString stringWithFormat:@"%@/%@", documentsDirectory,imgName];
                    
                    //NSLog(@"%@",filePath);
                    
                    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:filePath];
                        
                    if(!fileExists)
                    {
                        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                           
                            NSLog(@"Downloading Started");
                            NSString *urlToDownload = [NSString stringWithFormat:@"%@images/u/%@", self.serverDomain, imgName];
                            NSURL  *url = [NSURL URLWithString:urlToDownload];
                            NSData *urlData = [NSData dataWithContentsOfURL:url];
                            

                            if ( urlData )
                            {
                                //saving is done on main thread
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    [urlData writeToFile:filePath atomically:YES];
                                    NSLog(@"File Saved !");
                                });
                            }
                            
                        });
                    }

                    
                }
                
            }
            

            
        }
        @catch (NSException *exception) {
        }
    }

  

}


-(NSString *) sectionName: (SECTIONS) section
{
    switch(section)
    {
        case PESTS:
                return @"Pest";
            break;
        case PLANTLEAF:
                return @"PlantLeaf";
            break;
        case TUBERS:
                return @"Tuber";
            break;
        case TUTORIALS:
                return @"Tutorial";
            break;
    }
    
    return Nil;
}

-(void) queryBackend: (SECTIONS)reqSection
{
    NSString * sectionName = [self sectionName:reqSection];
    
    if(sectionName != Nil)
    {
        NSString *url =[NSString stringWithFormat:@"%@api/%@",self.serverDomain, sectionName];
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString: url]];
        
        NSOperationQueue *queue = [[NSOperationQueue alloc] init];
        
        [NSURLConnection sendAsynchronousRequest:request queue:queue completionHandler:^(NSURLResponse *response, NSData *data, NSError *error)
        {
            NSHTTPURLResponse *httpResponse = (NSHTTPURLResponse *)response;
            
            long int respCode = [httpResponse statusCode];
            
            if(respCode == 200)
            {
                NSLog(@"GOT data!!!");
                
                switch(reqSection)
                {
                    case PESTS:
                            [self updatePests: data];
                        break;
                    case PLANTLEAF:
                            [self updatePlantLeafs: data];
                        break;
                    case TUBERS:
                            [self updateTubers: data];
                        break;
                    case TUTORIALS:
                            [self updateTutorials: data];
                        break;
                }
            }
            else
            {
                NSLog(@"BOO, DIDNT GET MESSAGES!!! %d", respCode);
                
                [self.delegate didFailUpdating];
            }
        }];
    }
}

@end