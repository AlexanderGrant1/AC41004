//
//  Photo.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 28/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Pest, PlantLeaf, Tuber;

@interface Photo : NSManagedObject

@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSSet *pestPhotoRel;
@property (nonatomic, retain) NSSet *plantLeafRel;
@property (nonatomic, retain) NSSet *tuberPhotoRel;
@end

@interface Photo (CoreDataGeneratedAccessors)

- (void)addPestPhotoRelObject:(Pest *)value;
- (void)removePestPhotoRelObject:(Pest *)value;
- (void)addPestPhotoRel:(NSSet *)values;
- (void)removePestPhotoRel:(NSSet *)values;

- (void)addPlantLeafRelObject:(PlantLeaf *)value;
- (void)removePlantLeafRelObject:(PlantLeaf *)value;
- (void)addPlantLeafRel:(NSSet *)values;
- (void)removePlantLeafRel:(NSSet *)values;

- (void)addTuberPhotoRelObject:(Tuber *)value;
- (void)removeTuberPhotoRelObject:(Tuber *)value;
- (void)addTuberPhotoRel:(NSSet *)values;
- (void)removeTuberPhotoRel:(NSSet *)values;

@end
