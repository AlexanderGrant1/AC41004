<?php

class TuberController extends Controller
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
		$response['PhotoPath']    = Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'];
		$response['Entries']	  = array();
		$response['Photos']  	  = array();
		$response['PhotoLinker']  = array();
		$response['Tutorials']   = array();

		$tubers = Tuber::model()->findAll();


		if($tubers != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($tubers as $tuber) 
			{
				// Get all images for current tuber entry.
				$images    = $tuber->images;
				$tutorials = $images = $tuber->tuberTutorials;

				if($images != null)
				{
					foreach ($images as $image)
					{
						$response['PhotoLinker'][] = array('Id'=>$image->Id, 'EntryId' => $image->TuberId, 'PhotoId' => $image->PhotoId);
						$response['Photos'][] = array("Id" => $image->photo->Id, "ImageName" => $image->photo->Name, "EntryId" => $tuber->Id);
					}

					if($tutorials != null)
					{
						foreach ($tutorials as $tutorial)
						{
							$response['TutorialLinker'][] = array('Id'=>$tutorial->Id, 'EntryId' => $tutorial->TuberfId, 'TutorialId' => $tutorial->TutorialId);
						}
					}
				}

				$response['Entries'][]  = $tuber;
			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}