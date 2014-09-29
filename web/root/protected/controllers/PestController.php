<?php

class PestController extends Controller
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 */
	public $layout='//layouts/column2';

	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control for CRUD operations
			'postOnly + delete', // we only allow deletion via POST request
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
			array('allow',  // allow all users to perform 'index' and 'view' actions
				'actions'=>array('view'),
				'users'=>array('*'),
			),
			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('index','create','update', 'delete','delmedia'),
				'users'=>array('@'),
			),
			array('deny',  // deny all users
				'users'=>array('*'),
			),
		);
	}

	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionView($id)
	{
		$this->render('view',array(
			'model'=>$this->loadModel($id),
		));
	}

	/**
	 * Creates a new model.
	 * If creation is successful, the browser will be redirected to the 'view' page.
	 */
	public function actionCreate()
	{
		$model=new Pest;

		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);

		/* Retrieve a list of all tutorials */
		$tutorialData = Tutorial::model()->findAll(array('order' => 'Name'));
		$tutorialList = CHtml::listData($tutorialData, 'Id', 'Name');

		if(isset($_POST['Pest']))
		{
			$model->attributes=$_POST['Pest'];

			$this->manageTutorials($model->Id);

			if(isset($_POST['Pest']['image']))
        	{
	            $model->image=CUploadedFile::getInstance($model,'image');
	        }

			if($model->save())
				$this->redirect(array('view','id'=>$model->Id));
		}

		$this->render('create',array(
			'model'=>$model,
			'tutorialList' => $tutorialList
		));
	}

	/**
	 * Updates a particular model.
	 * If update is successful, the browser will be redirected to the 'view' page.
	 * @param integer $id the ID of the model to be updated
	 */
	public function actionUpdate($id)
	{
		$model=$this->loadModel($id);

		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);

		/* Retrieve a list of all tutorials */
		$tutorialData = Tutorial::model()->findAll(array('order' => 'Name'));
		$tutorialList = CHtml::listData($tutorialData, 'Id', 'Name');

		if(isset($_POST['Pest']))
		{
			$model->attributes=$_POST['Pest'];

			$this->manageTutorials($model->Id);

			if(isset($_POST['Pest']['image']))
        	{
	            $model->image=CUploadedFile::getInstance($model,'image');
	        }

			if($model->save())
				$this->redirect(array('view','id'=>$model->Id));
		}

		$this->render('update',array(
			'model'=>$model,
			'tutorialList' => $tutorialList
		));
	}

	/**
	 * Deletes a particular model.
	 * If deletion is successful, the browser will be redirected to the 'admin' page.
	 * @param integer $id the ID of the model to be deleted
	 */
	public function actionDelete($id)
	{
		$this->loadModel($id)->delete();

		// if AJAX request (triggered by deletion via admin grid view), we should not redirect the browser
		if(!isset($_GET['ajax']))
			$this->redirect(isset($_POST['returnUrl']) ? $_POST['returnUrl'] : array('admin'));
	}

	/**
	 * Manages all models.
	 */
	public function actionIndex()
	{
		$model=new Pest('search');
		$model->unsetAttributes();  // clear any default values
		if(isset($_GET['Pest']))
			$model->attributes=$_GET['Pest'];

		$this->render('index',array(
			'model'=>$model,
		));
	}

	/**
	 * Method for deleting media assigned to this
	 */
	public function actionDelmedia($id)
	{
		$model=PestPhoto::model()->findByPk($id);

		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');

		$model->delete();
	}

	/**
	 * Adds selected tutorials to a specific pest model.
	 * @param integer $id the ID of the model to be updated
	 */
	public function manageTutorials($id)
	{
		// Drop all previous relations.
		PestTutorial::model()->deleteAllByAttributes(array(
		    	'PestId'=>$id,
			));

		if(isset($_POST['Pest']['Tutorials']))
		{
			// Tutorials Have been added, create linker.
			foreach ($_POST['Pest']['Tutorials'] as $key => $value)
			{
				if(is_numeric($value))
				{
					// Check if such tutorial exists.
					$tutorialModel = Tutorial::model()->findByPk($value);

					if($tutorialModel != null)
					{
						// Tutorial exists, add it to the Pest.
						$criteria = new CDbCriteria();

						$criteria->condition = "PestId=:pest_id AND TutorialId=:tutorial_id";
						$criteria->params = array(':pest_id' => $id, ':tutorial_id'=>$value);

						$linker = PestTutorial::model()->findAll($criteria);

						// Just in case check if such relation does not exist already.
						if($linker == null)
						{
							$linkerModel = new PestTutorial;
							$linkerModel->PestId = $id;
							$linkerModel->TutorialId  = $value;

							$linkerModel->save();
						}
					}
				}
			}

			$model = $this->loadModel($id);
		}
	}


	/**
	 * Returns the data model based on the primary key given in the GET variable.
	 * If the data model is not found, an HTTP exception will be raised.
	 * @param integer $id the ID of the model to be loaded
	 * @return Pest the loaded model
	 * @throws CHttpException
	 */
	public function loadModel($id)
	{
		$model=Pest::model()->findByPk($id);
		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');
		return $model;
	}

	/**
	 * Performs the AJAX validation.
	 * @param Pest $model the model to be validated
	 */
	protected function performAjaxValidation($model)
	{
		if(isset($_POST['ajax']) && $_POST['ajax']==='pest-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}
}