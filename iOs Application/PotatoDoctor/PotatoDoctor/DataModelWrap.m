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
        NSLog(@"AA");
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

-(NSString*) getPicture
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

@end
