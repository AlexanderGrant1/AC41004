//
//  PestPhoto.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Pest;

@interface PestPhoto : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSNumber * pestId;
@property (nonatomic, retain) NSNumber * photoId;
@property (nonatomic, retain) Pest *pestRel;
@property (nonatomic, retain) NSManagedObject *photoRel;

@end
