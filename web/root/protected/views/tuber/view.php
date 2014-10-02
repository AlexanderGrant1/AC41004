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
	array('label'=>'Delete Tuber Symptoms', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
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
