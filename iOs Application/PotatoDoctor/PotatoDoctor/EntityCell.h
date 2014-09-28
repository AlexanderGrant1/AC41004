//
//  EntityCell.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DataModelWrap.h"

@interface EntityCell : UICollectionViewCell

@property (nonatomic, strong) IBOutlet UIImageView *imageView; 
@property (weak, nonatomic) IBOutlet UILabel *textLabel;

@property (nonatomic, strong) DataModelWrap* dataModel;


-(void) refreshMe;

@end
