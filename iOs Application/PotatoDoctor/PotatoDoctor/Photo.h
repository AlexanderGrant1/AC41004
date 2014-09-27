//
//  Photo.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class PestPhoto, PlantLeafPhoto, TuberPhoto;

@interface Photo : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) PestPhoto *pestPhotoRel;
@property (nonatomic, retain) PlantLeafPhoto *plantLeafRel;
@property (nonatomic, retain) TuberPhoto *tuberPhotoRel;

@end
