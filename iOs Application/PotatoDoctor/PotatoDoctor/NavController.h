//
//  NavController.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 23/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DbManager.h"
#import "EntityCell.h"
#import "DataModelWrap.h"
#import "EntityDetailViewController.h"

@interface NavController : UIViewController<UICollectionViewDataSource,UICollectionViewDelegateFlowLayout>

@property(nonatomic, weak) IBOutlet UICollectionView *collectionView;

typedef enum{
    PESTS, PLANTLEAF, TUBERS, TUTORIALS
} SECTIONS;

@property (nonatomic) SECTIONS section;

@property (nonatomic, strong) NSMutableArray *data;

@end

