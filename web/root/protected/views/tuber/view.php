<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tuber Symptoms'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Tuber Symptoms', 'url'=>array('index')),
	array('label'=>'Create Tuber Symptoms', 'url'=>array('create')),
	array('label'=>'Update Tuber Symptoms', 'url'=>array('update', 'id'=>$model->Id)),
);
?>

<h1>View Tuber Symptom "<?php echo $model->Name; ?>"</h1>

<?php $this->widget('bootstrap.widgets.TbDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Name',
		'Description',
	),
)); ?>

<?php echo $this->renderPartial('//site/_imageManager', array('model'=>$model, 'canDelete' => false)); ?>
