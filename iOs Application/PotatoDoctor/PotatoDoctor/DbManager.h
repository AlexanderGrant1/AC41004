//
//  DbManager.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

#import "Pest.h"
#import "PestPhoto.h"
#import "Photo.h"
#import "Tuber.h"
#import "TuberPhoto.h"
#import "PlantLeaf.h"
#import "PlantLeafPhoto.h"
#import "Tutorial.h"


@interface DbManager : NSObject

@property (strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;
@property (strong, nonatomic) NSPersistentStore *persistentStore;
@property (strong, nonatomic) NSURL *persistentURL;

- (void)setupManagedObjectContext;

- (NSArray *) getPests;
- (NSArray *) getTubers;
- (NSArray *) getPlantLeafs;


+ (DbManager *)sharedManager;

- (void)saveDataInManagedContextUsingBlock:(void (^)(BOOL saved, NSError *error))savedBlock;

- (NSFetchedResultsController *)fetchEntitiesWithClassName:(NSString *)className
                                           sortDescriptors:(NSArray *)sortDescriptors
                                        sectionNameKeyPath:(NSString *)sectionNameKeypath
                                                 predicate:(NSPredicate *)predicate;

- (id)createEntityWithClassName:(NSString *)className
           attributesDictionary:(NSDictionary *)attributesDictionary;
- (void)deleteEntity:(NSManagedObject *)entity;
- (BOOL)uniqueAttributeForClassName:(NSString *)className
                      attributeName:(NSString *)attributeName
                     attributeValue:(id)attributeValue;

-(void) clearTables;
-(void) clearTable: (NSString*) tableName;
- (void)saveContext;

@end