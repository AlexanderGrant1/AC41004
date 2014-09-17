<?php

class NutrientdeficiencyController extends Controller
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
		// Return all Diseases
		$response = array();

		$nutrientDeficiencies = NutrientDeficiency::model()->findAll();


		if($nutrientDeficiencies != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($nutrientDeficiencies as $nutrientDeficiency) 
			{
				// Get all images for current nutrientDeficiency entry.
				$images = $nutrientDeficiency->images;
				$imageArr = array();

				if($images != null)
				{
					foreach ($images as $image)
					{
					
						$imageArr[$nutrientDeficiency->Id] = array($image->Id=>'http://beberry.lv/potato/images/'.$image->photo->Name);
					}
				}

				$response['Entries'][]  = $nutrientDeficiency;
				$response['Images'][] = $imageArr;
			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}