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

	/**
	 * Return all Pests
	 */
	public function actionIndex()
	{
		$response = array();
		$response['PhotoPath']   = Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'];
		$response['Entries']  	 = array();
		$response['Photos']  	 = array();
		$response['PhotoLinker'] = array();
		$response['Tutorials']   = array();

		$pestModels = Pest::model()->findAll();


		if($pestModels != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($pestModels as $pestModel) 
			{
				// Get all images for current pestModel entry.
				$images    = $pestModel->images;
				$tutorials = $pestModel->pestTutorials;

				if($images != null)
				{
					foreach ($images as $image)
					{
						$response['PhotoLinker'][] = array('Id'=>$image->Id, 'EntryId' => $image->PestId, 'PhotoId' => $image->PhotoId);
						$response['Photos'][] = array("Id" => $image->photo->Id, "ImageName" => $image->photo->Name, "EntryId" => $pestModel->Id);
					}
				}

				if($tutorials != null)
				{
					foreach ($tutorials as $tutorial)
					{
						$response['TutorialLinker'][] = array('Id'=>$tutorial->Id, 'EntryId' => $tutorial->PestId, 'TutorialId' => $tutorial->TutorialId);
					}
				}

				$response['Entries'][]  = $pestModel;

			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}