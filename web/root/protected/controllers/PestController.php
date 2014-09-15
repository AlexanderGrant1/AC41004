<?php

class PestController extends Controller
{

	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control for CRUD operations
			//'postOnly + delete', // we only allow deletion via POST request
		);
	}

	/**
	 * Specifies the access control rules.
	 * This method is used by the 'accessControl' filter.
	 * @return array access control rules
	 */
	public function accessRules()
	{
		return array(

			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('create','delete'),
				'users'=>array('*'),
			),
		);
	}

	public function actionIndex()
	{
		// Return all Pests
		$response = array();

		$pests = Pest::model()->findAll();


		if($pests != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($pests as $pest) 
			{
				// Get all images for current pest entry.
				$images = $pest->images;
				$imageArr = array();

				if($images != null)
				{
					//$pest['images'] = array();
					foreach ($images as $image)
					{
					
						$imageArr[$pest->Id] = array($image->Id=>'http://beberry.lv/potato/images/'.$image->photo->Name);
					}
				}

				$response['Entries'][]  = $pest;
				$response['Images'][] = $imageArr;
			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}