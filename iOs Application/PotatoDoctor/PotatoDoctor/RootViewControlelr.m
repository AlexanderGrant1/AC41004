//
//  RootViewControlelr.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 26/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RootViewController.h"

@implementation RootViewController

- (IBAction)pestClick:(id)sender {
   
    NavController *controller = [self.storyboard instantiateViewControllerWithIdentifier:@"ThumbStory"];
    
    controller.section = PESTS;
    
    [self.navigationController pushViewController:controller animated:YES];
}

- (IBAction)tuberClick:(id)sender {
    NavController *controller = [self.storyboard instantiateViewControllerWithIdentifier:@"ThumbStory"];
    
    controller.section = TUBERS;
    
    
    [self.navigationController pushViewController:controller animated:YES];

}
- (IBAction)PlantLeafClick:(id)sender {
    NavController *controller = [self.storyboard instantiateViewControllerWithIdentifier:@"ThumbStory"];
    
    controller.section = PLANTLEAF;
    
    [self.navigationController pushViewController:controller animated:YES];

}

@end
