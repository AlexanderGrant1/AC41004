<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant Leaves'=>array('index'),
	$model->Name,
);

$this->menu=array(
	array('label'=>'List PlantLeaf', 'url'=>array('index')),
	array('label'=>'Create PlantLeaf', 'url'=>array('create')),
	array('label'=>'Update PlantLeaf', 'url'=>array('update', 'id'=>$model->Id)),
	array('label'=>'Delete PlantLeaf', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->Id),'confirm'=>'Are you sure you want to delete this item?')),
);
?>

<h1>View PlantLeaf #<?php echo $model->Id; ?></h1>

<?php $this->widget('bootstrap.widgets.TbDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'Id',
		'Name',
		'Description',
	),
)); ?>
