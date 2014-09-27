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
    
    
    NSArray *entries = [responseArray valueForKey:@"Entries"];
    NSArray *photos = [responseArray valueForKey:@"Photos"];
    
    NSManagedObjectContext *context = [[DbManager sharedManager] managedObjectContext];
    
    for(NSDictionary *entity in entries)
    {
        // Add Entry to the db.
        
        @try{
            NSString *description = [entity objectForKey:@"Description"];
            NSString *name        = [entity objectForKey:@"Name"];
            NSInteger objId       = [[entity objectForKey:@"Id"] intValue];
            
            NSManagedObject *entries = [NSEntityDescription
                                            insertNewObjectForEntityForName:tableName
                                            inManagedObjectContext:context];
            
            [entries setValue:[NSNumber numberWithInt:objId] forKey:@"id"];
            [entries setValue:name forKey:@"name"];
            [entries setValue:description forKey:@"descriptionText"];
            
            
            // Get all photos for this entry ( really bad way, don't have time for proper implementation, sorry.. ;( )
            for(NSDictionary *photo in photos)
            {
                // Check whether the id matches the id of this entry.
                NSInteger entryId = [[photo objectForKey:@"Id"] intValue];
                
                if(entryId == objId)
                {
                    // This photo belongs to this entry.
                    
                    // Add the photo to the
                    NSString *imgName = [photo objectForKey:@"ImageName"];
                    NSInteger entryId = [[photo objectForKey:@"Id"] intValue];
                    
                    Photo *photo = (Photo *)[NSEntityDescription
                                               insertNewObjectForEntityForName:@"Photo"
                                               inManagedObjectContext:context];
                    
                    photo.name = imgName;
                    
                    
                    // Create the linker
                    switch(section)
                    {
                        case PESTS:
                            {
                            // Add Pest photo linker.
                            PestPhoto *photoLinker = (PestPhoto *)[NSEntityDescription
                                                                   insertNewObjectForEntityForName:@"PestPhoto"
                                                                    inManagedObjectContext:context];

                                photoLinker.pestRel = (Pest *)entries;
                                photoLinker.photoRel = photo;
                                
                            // Get the pest object
                            
                            break;
                            }
                        case PLANTLEAF:
                            {
                                // Add plant leaf photo linker.
                                PlantLeafPhoto *photoLinker = (PlantLeafPhoto *)[NSEntityDescription
                                                                       insertNewObjectForEntityForName:@"PlantLeafPhoto"
                                                                       inManagedObjectContext:context];
                                
                                photoLinker.plantLeafRel = (PlantLeaf *)entries;
                                photoLinker.photoRel     = photo;
                                
                                // Get the pest object
                                
                                
                                break;
                            }
                            
                        case TUBERS:
                            {
                                // Add plant leaf photo linker.
                                TuberPhoto *photoLinker = (TuberPhoto *)[NSEntityDescription
                                                                                 insertNewObjectForEntityForName:@"TuberPhoto"
                                                                                 inManagedObjectContext:context];
                                
                                photoLinker.tuberRel = (Tuber *)entries;
                                photoLinker.photoRel = photo;
                                
                                // Get the pest object
                                
                                
                                break;
                            }
                            

                    }

                    
                }
            }
            
            
            
        }
        @catch (NSException *exception) {
        }
    }
    
    
    //NSError *error;
    //if (![context save:&error]) {
     //   NSLog(@"Whoops, couldn't save: %@", [error localizedDescription]);
  //  }

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
                return @"Tutorial";\
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