//
//  DataModelWrap.m
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 27/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import "DataModelWrap.h"


@implementation DataModelWrap

-(NSString*) getName
{
    if(self.pestModel != Nil)
    {
        return self.pestModel.name;
    }
    else if(self.tuberModel != Nil)
    {
        return self.tuberModel.name;
    }
    else if(self.tutorialModel != Nil)
    {
        return self.tutorialModel.name;
    }
    else if(self.plantLeafModel != Nil)
    {
        return self.plantLeafModel.name;
    }
    
    
    return @"";
}

-(NSString*) getDescription
{
    if(self.pestModel != Nil)
    {
        return self.pestModel.descriptionText;
    }
    else if(self.tuberModel != Nil)
    {
        return self.tuberModel.descriptionText;
    }
    else if(self.tutorialModel != Nil)
    {
        return self.tutorialModel.descriptionText;
    }
    else if(self.plantLeafModel != Nil)
    {
        return self.plantLeafModel.descriptionText;
    }
    
    
    return @"";

}

-(UIImage *) getMainPhoto
{
    NSString *imgName = @"";
    
    if(self.pestModel != Nil)
    {
        NSArray *myArray = [(NSSet*)self.pestModel.photoRel allObjects];
        
        if([myArray count] > 0)
        {
            Photo *photo = (Photo *)[myArray objectAtIndex:0];
            imgName = photo.name;
        }
    }
    else if(self.tuberModel != Nil)
    {
        NSArray *myArray = [(NSSet*)self.tuberModel.photoRel allObjects];
        
        if([myArray count] > 0)
        {
            Photo *photo = (Photo *)[myArray objectAtIndex:0];
            imgName = photo.name;
        }
    }
    else if(self.plantLeafModel != Nil)
    {
        NSArray *myArray = [(NSSet*)self.plantLeafModel.photoRel allObjects];
        
        if([myArray count] > 0)
        {
            Photo *photo = (Photo *)[myArray objectAtIndex:0];
            imgName = photo.name;
        }
    }
    
    NSArray *paths               = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    
    NSString *filePath  = [NSString stringWithFormat:@"%@/%@", documentsDirectory,imgName];
    UIImage *coverImage = [[UIImage alloc] initWithContentsOfFile:[NSString stringWithFormat:@"%@",filePath]];
    
    return coverImage;
}

@end
