//
//  Pest.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 28/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Photo;

@interface Pest : NSManagedObject

@property (nonatomic, retain) NSString * descriptionText;
@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSOrderedSet *photoRel;
@end

@interface Pest (CoreDataGeneratedAccessors)

- (void)insertObject:(Photo *)value inPhotoRelAtIndex:(NSUInteger)idx;
- (void)removeObjectFromPhotoRelAtIndex:(NSUInteger)idx;
- (void)insertPhotoRel:(NSArray *)value atIndexes:(NSIndexSet *)indexes;
- (void)removePhotoRelAtIndexes:(NSIndexSet *)indexes;
- (void)replaceObjectInPhotoRelAtIndex:(NSUInteger)idx withObject:(Photo *)value;
- (void)replacePhotoRelAtIndexes:(NSIndexSet *)indexes withPhotoRel:(NSArray *)values;
- (void)addPhotoRelObject:(Photo *)value;
- (void)removePhotoRelObject:(Photo *)value;
- (void)addPhotoRel:(NSOrderedSet *)values;
- (void)removePhotoRel:(NSOrderedSet *)values;
@end
