<?php

class TutorialController extends Controller
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
		$response['VideoPath']    = Yii::app()->params['siteDomain'].Yii::app()->params['videoPath'];
		$response['Entries']	  = array();

		$tutorials = Tutorial::model()->findAll();


		if($tutorials != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($tutorials as $tutorial) 
			{
				// Get all images for current tutorial entry.

				$response['Entries'][]  = array('Name' => $tutorial->Name, 'Description' => $tutorial->Description, 'VideoName' =>$tutorial->VideoName);

			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}