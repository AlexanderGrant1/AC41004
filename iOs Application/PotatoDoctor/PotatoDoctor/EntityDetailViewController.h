//
//  EntityDetailViewController.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DataModelWrap.h"

@interface EntityDetailViewController : UIViewController


@property (weak, nonatomic) IBOutlet UITextView *textLabel;
@property (nonatomic, strong) DataModelWrap* dataModel;
@property (weak, nonatomic) IBOutlet UIImageView *mainImageView;

-(void) refreshMe;

@end
