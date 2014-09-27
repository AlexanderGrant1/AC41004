//
//  Updater.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 25/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DbManager.h"

#import "PlantLeaf.h"
#import "Photo.h"
#import "PestPhoto.h"
#import "Pest.h"
#import "TuberPhoto.h"
#import "Tutorial.h"
#import "PlantLeafPhoto.h"
#import "Tuber.h"
#import "DataModelWrap.h"


@protocol UpdaterDelegate <NSObject>

- (void)didUpdate;
- (void)didFailUpdating;

@end


@interface Updater : NSObject

@property (nonatomic, weak) id<UpdaterDelegate>delegate;
@property (nonatomic, copy) NSString *serverDomain;


typedef enum{
    PESTS, PLANTLEAF, TUBERS, TUTORIALS
} SECTIONS;

@property SECTIONS apiSections;

//-(NSMutableArray *) parseMessages: (NSData *)serverResponse;
-(void) queryBackend;
-(void) updatePests: (NSData *) data;
-(void) updatePlantLeafs: (NSData *) data;
-(void) updateTubers: (NSData *) data;
-(void) updateTutorials: (NSData *) data;

-(void) queryBackend: (SECTIONS)reqSection;

-(NSString *) sectionName: (SECTIONS) section;


@end
