//
//  DataModelWrap.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Pest.h"
#import "Tuber.h"
#import "PlantLeaf.h"
#import "Tutorial.h"


@interface DataModelWrap : NSObject

@property (nonatomic, weak) Pest* pestModel;
@property (nonatomic, weak) Tuber* tuberModel;
@property (nonatomic, weak) PlantLeaf* plantLeafModel;
@property (nonatomic, weak) Tutorial* tutorialModel;

-(NSString*) getName;
-(NSString*) getDescription;
-(NSString*) getPicture;

@end
