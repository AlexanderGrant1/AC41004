<?php
/* @var $this PestController */
/* @var $model Pest */

$this->breadcrumbs=array(
	'Pests'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Pest', 'url'=>array('index')),
	array('label'=>'Create Pest', 'url'=>array('create')),
	array('label'=>'View Pest', 'url'=>array('view', 'id'=>$model->Id)),
);
?>

<h1>Update Pest "<?php echo $model->Name; ?>"</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model,'tutorialList' => $tutorialList)); ?>