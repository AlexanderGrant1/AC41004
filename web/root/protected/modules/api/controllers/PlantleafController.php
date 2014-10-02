<?php

class PlantLeafController extends Controller
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
	 * Return all Plant/leaf objects
	 */
	public function actionIndex()
	{
		$response = array();
		$response['PhotoPath']   = Yii::app()->params['siteDomain'].Yii::app()->params['imagePath'];
		$response['Entries']	 = array();
		$response['Photos']  	 = array();
		$response['PhotoLinker'] = array();

		$plantLeafModels = PlantLeaf::model()->findAll();


		if($plantLeafModels != null)
		{
			// Have something to return..

			// Get images for each entry.
			foreach ($plantLeafModels as $plantLeafModel) 
			{
				// Get all images for current plantLeafModel entry.
				$images = $plantLeafModel->images;

				if($images != null)
				{
					foreach ($images as $image)
					{
						$response['PhotoLinker'][] = array('Id'=>$image->Id, 'EntryId' => $image->PlantLeafId, 'PhotoId' => $image->PhotoId);
						$response['Photos'][] = array("Id" => $image->photo->Id, "ImageName" => $image->photo->Name, "EntryId" => $plantLeafModel->Id);
					}
				}

				$response['Entries'][]  = $plantLeafModel;

			}
		}

		$this->sendResponse(200, CJSON::encode($response));
	}
}