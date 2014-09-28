//
//  EntityDetailViewController.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "EntityDetailViewController.h"

@interface EntityDetailViewController ()

@end

@implementation EntityDetailViewController

- (void)viewDidLoad {

    [super viewDidLoad];
    // Do any additional setup after loading the view.
        [self refreshMe];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void) refreshMe
{
    [self.textLabel setText:[self.dataModel getDescription]];
    [self.mainImageView setImage: [self.dataModel getMainPhoto]];

    self.mainImageView.contentMode  = UIViewContentModeScaleAspectFit;
}


@end
