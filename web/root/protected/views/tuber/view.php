<?php
/* @var $this TuberController */
/* @var $model Tuber */

$this->breadcrumbs=array(
	'Tubers'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Tuber', 'url'=>array('index')),
	array('label'=>'Create Tuber', 'url'=>array('create')),
	array('label'=>'Update Tuber', 'url'=>array('update', 'id'=>$model->Id)),
	array('label'=>'Delete Tuber', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Tuber', 'url'=>array('admin')),
);
?>

<h1>View Tuber #<?php echo $model->Id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Id',
		'Name',
		'Description',
	),
)); ?>
