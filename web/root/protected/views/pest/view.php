<?php
/* @var $this PestController */
/* @var $model Pest */

$this->breadcrumbs=array(
	'Pests'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List Pest', 'url'=>array('index')),
	array('label'=>'Create Pest', 'url'=>array('create')),
	array('label'=>'Update Pest', 'url'=>array('update', 'id'=>$model->Id)),
	array('label'=>'Delete Pest', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Pest', 'url'=>array('admin')),
);
?>

<h1>View Pest #<?php echo $model->Id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Id',
		'Name',
		'Description',
	),
)); ?>
