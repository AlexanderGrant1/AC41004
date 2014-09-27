//
//  PlantLeafPhoto.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface PlantLeafPhoto : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSNumber * pihotoId;
@property (nonatomic, retain) NSNumber * plantLeafId;
@property (nonatomic, retain) NSManagedObject *photoRel;
@property (nonatomic, retain) NSManagedObject *plantLeafRel;

@end
