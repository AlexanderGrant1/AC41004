<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tuber Symptoms'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Tuber Symptoms', 'url'=>array('index')),
);
?>

<h1>Create Tuber Symptoms</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model,'tutorialList' => $tutorialList)); ?>