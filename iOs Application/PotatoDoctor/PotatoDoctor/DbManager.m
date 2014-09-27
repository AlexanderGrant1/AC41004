//
//  DbManager.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "DbManager.h"


@implementation DbManager

static DbManager *coreDataManager;

+ (DbManager *)sharedManager
{
    if (!coreDataManager) {
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
            coreDataManager = [[DbManager alloc] init];
        });
        
    }
    
    return coreDataManager;
}

#pragma mark - setup

- (id)init
{
    self = [super init];
    
    if (self) {
        [self setupManagedObjectContext];
    }
    
    return self;
}

- (void)setupManagedObjectContext
{
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    NSURL *documentDirectoryURL = [fileManager URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask][0];
    
    self.persistentURL = [documentDirectoryURL URLByAppendingPathComponent:@"PotatoDoctor.sqlite"];
    
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"PotatoDoctor" withExtension:@"momd"];
    
    self.managedObjectModel = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    self.persistentStoreCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:self.managedObjectModel];
    
    NSError *error = nil;
    self.persistentStore = [self.persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType
                                                                                       configuration:nil
                                                                                                 URL:self.persistentURL
                                                                                             options:nil
                                                                                               error:&error];
    if (self.persistentStore) {
        self.managedObjectContext = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSMainQueueConcurrencyType];
        
        self.managedObjectContext.persistentStoreCoordinator = self.persistentStoreCoordinator;
    } else {
        NSLog(@"ERROR: %@", error.description);
    }
}

- (void)saveDataInManagedContextUsingBlock:(void (^)(BOOL saved, NSError *error))savedBlock
{
    NSError *saveError = nil;
    savedBlock([self.managedObjectContext save:&saveError], saveError);
}

- (NSFetchedResultsController *)fetchEntitiesWithClassName:(NSString *)className
                                           sortDescriptors:(NSArray *)sortDescriptors
                                        sectionNameKeyPath:(NSString *)sectionNameKeypath
                                                 predicate:(NSPredicate *)predicate

{
    NSFetchedResultsController *fetchedResultsController;
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:className
                                              inManagedObjectContext:self.managedObjectContext];
    fetchRequest.entity = entity;
    fetchRequest.sortDescriptors = sortDescriptors;
    fetchRequest.predicate = predicate;
    
    fetchedResultsController = [[NSFetchedResultsController alloc] initWithFetchRequest:fetchRequest
                                                                   managedObjectContext:self.managedObjectContext
                                                                     sectionNameKeyPath:sectionNameKeypath
                                                                              cacheName:nil];
    
    NSError *error = nil;
    BOOL success = [fetchedResultsController performFetch:&error];
    
    if (!success) {
        NSLog(@"fetchManagedObjectsWithClassName ERROR: %@", error.description);
    }
    
    return fetchedResultsController;
}

- (id)createEntityWithClassName:(NSString *)className
           attributesDictionary:(NSDictionary *)attributesDictionary
{
    NSManagedObject *entity = [NSEntityDescription insertNewObjectForEntityForName:className
                                                            inManagedObjectContext:self.managedObjectContext];
    [attributesDictionary enumerateKeysAndObjectsUsingBlock:^(NSString *key, id obj, BOOL *stop) {
        
        [entity setValue:obj forKey:key];
        
    }];
    
    return entity;
}

- (void)deleteEntity:(NSManagedObject *)entity
{
    [self.managedObjectContext deleteObject:entity];
}

- (BOOL)uniqueAttributeForClassName:(NSString *)className
                      attributeName:(NSString *)attributeName
                     attributeValue:(id)attributeValue
{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"%K like %@", attributeName, attributeValue];
    NSArray *sortDescriptors = @[[NSSortDescriptor sortDescriptorWithKey:attributeName ascending:YES]];
    
    NSFetchedResultsController *fetchedResultsController = [self fetchEntitiesWithClassName:className
                                                                            sortDescriptors:sortDescriptors
                                                                         sectionNameKeyPath:nil
                                                                                  predicate:predicate];
    
    return fetchedResultsController.fetchedObjects.count == 0;
}

-(void) clearTables
{
    [self clearTable:@"Pest"];
    [self clearTable:@"PestPhoto"];
    [self clearTable:@"TuberPhoto"];
    [self clearTable:@"PlantLeafPhoto"];
    [self clearTable:@"Tutorial"];
    [self clearTable:@"Photo"];
    [self clearTable:@"Tuber"];
    [self clearTable:@"PlantLeaf"];
}

-(void) clearTable: (NSString*) tableName
{
    /*
    NSError *error;
    [self.managedObjectContext reset];
    
    
    if([self.persistentStoreCoordinator removePersistentStore:self.persistentStore error:&error])
    {
        NSLog(@"Store removed");
         [[NSFileManager defaultManager] removeItemAtPath:self.persistentURL.path error:&error];
        
        // Create new persisten store.
        self.persistentStore = [self.persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType
                                                                             configuration:nil
                                                                                       URL:self.persistentURL
                                                                                   options:nil
                                                                                     error:&error];
        
        [self.managedObjectContext reset];

    }
    else{
        NSLog(@"Store was not removed");
    }
     */
    
    NSFetchRequest * allPests = [[NSFetchRequest alloc] init];
    [allPests setEntity:[NSEntityDescription entityForName:[NSString stringWithFormat:@"%@",tableName] inManagedObjectContext:self.managedObjectContext]];
    [allPests setIncludesPropertyValues:NO]; //only fetch the managedObjectID
    
    NSError * error = nil;
    NSArray * pests = [self.managedObjectContext executeFetchRequest:allPests error:&error];

    
    //error handling goes here
    for (NSManagedObject * pest in pests) {
        [self.managedObjectContext deleteObject:pest];
    }
    
    
    NSError *saveError = nil;
    [self.managedObjectContext save:&saveError];
    
    
    

    
    
    
    
    
    
}

- (void)saveContext {
    NSManagedObjectContext *managedObjectContext = self.managedObjectContext;
    if (managedObjectContext != nil) {
        NSError *error = nil;
        if ([managedObjectContext hasChanges] && ![managedObjectContext save:&error]) {
            // Replace this implementation with code to handle the error appropriately.
            // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
            abort();
        }
    }
}

- (NSArray *) getPests
{

    NSManagedObjectContext *context = [self managedObjectContext];
      NSError *error = nil;
    // Test listing all FailedBankInfos from the store
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:@"Pest"
                                              inManagedObjectContext:context];
    [fetchRequest setEntity:entity];
    NSArray *fetchedObjects = [context executeFetchRequest:fetchRequest error:&error];
   // for (Pest *pest in fetchedObjects) {
        // NSLog(@"id: %@", pest.objectID);
       // NSLog(@"Name: %@", pest.name);
       // NSLog(@"des: %@", pest.descriptionText);
        
  //  }
    
    return fetchedObjects;
}

- (NSArray *) getTubers
{
    
    NSManagedObjectContext *context = [self managedObjectContext];
    NSError *error = nil;
    // Test listing all FailedBankInfos from the store
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:@"Tuber"
                                              inManagedObjectContext:context];
    [fetchRequest setEntity:entity];
    NSArray *fetchedObjects = [context executeFetchRequest:fetchRequest error:&error];
    // for (Pest *pest in fetchedObjects) {
    // NSLog(@"id: %@", pest.objectID);
    // NSLog(@"Name: %@", pest.name);
    // NSLog(@"des: %@", pest.descriptionText);
    
    //  }
    
    return fetchedObjects;
}

- (NSArray *) getPlantLeafs
{
    
    NSManagedObjectContext *context = [self managedObjectContext];
    NSError *error = nil;
    // Test listing all FailedBankInfos from the store
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:@"PlantLeaf"
                                              inManagedObjectContext:context];
    [fetchRequest setEntity:entity];
    NSArray *fetchedObjects = [context executeFetchRequest:fetchRequest error:&error];
    // for (Pest *pest in fetchedObjects) {
    // NSLog(@"id: %@", pest.objectID);
    // NSLog(@"Name: %@", pest.name);
    // NSLog(@"des: %@", pest.descriptionText);
    
    //  }
    
    return fetchedObjects;
}

@end