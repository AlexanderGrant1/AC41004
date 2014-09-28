//
//  NavController.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 23/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "NavController.h"

@interface NavController ()

@end

@implementation NavController

- (id)initWithCoder:(NSCoder *)aDecoder {
    
    self = [super initWithCoder:aDecoder];
    
    if (self) {
        // Custom initialization
        self.data = [[NSMutableArray alloc] init];
    }
    
    return self;
}

- (void)viewDidLoad
{
    [self addCells];
    [super viewDidLoad];
}


-(void)addCells
{
    NSArray * pests = [[NSArray alloc]init];
    
    if(self.section == PESTS)
    {
        pests = [[DbManager sharedManager] getPests];
    }
    
    
    switch(self.section)
    {
        case PESTS:
            pests = [[DbManager sharedManager] getPests];
            break;
        case PLANTLEAF:
            pests = [[DbManager sharedManager] getPlantLeafs];
            break;
        case TUBERS:
            pests = [[DbManager sharedManager] getTubers];
            break;
    }
    
    [self.data addObjectsFromArray:pests];
}



#pragma mark - UICollectionView Datasource

- (NSInteger)collectionView:(UICollectionView *)view numberOfItemsInSection:(NSInteger)section {
    return [self.data count];
}

- (NSInteger)numberOfSectionsInCollectionView: (UICollectionView *)collectionView {
    return 1;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)cv cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    EntityCell *cell = [cv dequeueReusableCellWithReuseIdentifier:@"EntityCell" forIndexPath:indexPath];
  
    DataModelWrap *dmw = [[DataModelWrap alloc] init];
    
    switch(self.section)
    {
        case PESTS:
                dmw.pestModel = self.data[indexPath.row];
            break;
        case PLANTLEAF:
                dmw.plantLeafModel = self.data[indexPath.row];
            break;
        case TUBERS:
                dmw.tuberModel = self.data[indexPath.row];
            break;
    }
    
     
    cell.dataModel = dmw;
    [cell refreshMe];
    

    return cell;
}


- (void)collectionView:(UITableView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    EntityDetailViewController *controller = [self.storyboard instantiateViewControllerWithIdentifier:@"EntityDetailVc"];
    
    
    DataModelWrap *dmw = [[DataModelWrap alloc] init];
    
    switch(self.section)
    {
        case PESTS:
            dmw.pestModel = self.data[indexPath.row];
            break;
        case PLANTLEAF:
            dmw.plantLeafModel = self.data[indexPath.row];
            break;
        case TUBERS:
            dmw.tuberModel = self.data[indexPath.row];
            break;
    }
    
    controller.dataModel = dmw;

    [self.navigationController pushViewController:controller animated:YES];
}

@end
