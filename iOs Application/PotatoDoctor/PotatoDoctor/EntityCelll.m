//
//  EntityCell.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "EntityCell.h"

@implementation EntityCell

-(void) refreshMe
{
    [self.textLabel setText:[self.dataModel getName]];
    [self.imageView setImage: [self.dataModel getMainPhoto]];
}

@end
