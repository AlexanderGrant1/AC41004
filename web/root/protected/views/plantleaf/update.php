<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant Leaves'=>array('index'),
	$model->Name=>array('view','id'=>$model->Id),
	'Update',
);

$this->menu=array(
	array('label'=>'List PlantLeaf', 'url'=>array('index')),
	array('label'=>'Create PlantLeaf', 'url'=>array('create')),
	array('label'=>'View PlantLeaf', 'url'=>array('view', 'id'=>$model->Id)),
);
?>

<h1>Update PlantLeaf <?php echo $model->Id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>