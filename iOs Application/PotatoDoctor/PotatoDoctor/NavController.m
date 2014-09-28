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
   // self.view = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    
    /*_collectionView.dataSource = self;
    _collectionView.delegate = self;
    
    UICollectionViewFlowLayout *layout=[[UICollectionViewFlowLayout alloc] init];
    _collectionView=[[UICollectionView alloc] initWithFrame:self.view.frame collectionViewLayout:layout];
    [_collectionView setDataSource:self];
    [_collectionView setDelegate:self];
    
    [_collectionView registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"cellIdentifier"];
    
    
    [self.view addSubview:_collectionView];
    
    
    [_collectionView registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"EntryCell"];*/
    
   // [self.data = [[DbManager sharedManager] get]]
    
    [self addCells];
    [super viewDidLoad];
    
    
    
   // dispatch_async(dispatch_get_main_queue(), ^{
        // Placeholder: reload collectionview data
  //  });
  // [self.collectionView reloadData];
    
    // Do any additional setup after loading the view, typically from a nib.
}
- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation{
    
    
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
    NSLog(@"%d",pests.count);
    
    
    /*[_collectionView performBatchUpdates:^{
        // Insert the cut/copy items into data source as well as collection view
        
        NSIndexPath *indexPath = [NSIndexPath indexPathForItem:0 inSection:0];
        
        for (id item in pests) {
            // update your data source array
            [self.data insertObject:item atIndex:indexPath.row];
            
            [_collectionView insertItemsAtIndexPaths:
             [NSArray arrayWithObject:indexPath]];
        }
    } completion:nil];*/
}



#pragma mark - UICollectionView Datasource
// 1
- (NSInteger)collectionView:(UICollectionView *)view numberOfItemsInSection:(NSInteger)section {
    NSLog(@"%d", [self.data count]);
    return [self.data count];
}
// 2
- (NSInteger)numberOfSectionsInCollectionView: (UICollectionView *)collectionView {
    return 1;
}
// 3
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



// The cell that is returned must be retrieved from a call to -dequeueReusableCellWithReuseIdentifier:forIndexPath:
/*- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    UICollectionViewCell *cell=[collectionView dequeueReusableCellWithReuseIdentifier:@"cellIdentifier" forIndexPath:indexPath];
    
    cell.backgroundColor=[UIColor greenColor];
    return cell;
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return self.data.count;
}
*/

@end
