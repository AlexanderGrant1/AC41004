<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tuber Symptoms'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Tuber Symptoms', 'url'=>array('index')),
	array('label'=>'Create Tuber Symptoms', 'url'=>array('create')),
	array('label'=>'View Tuber Symptoms', 'url'=>array('view', 'id'=>$model->Id)),
);
?>

<h1>Update Tuber Symptom "<?php echo $model->Name; ?>"</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model,'tutorialList' => $tutorialList)); ?>