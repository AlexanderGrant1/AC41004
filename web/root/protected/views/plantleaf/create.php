<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant Leaves'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List PlantLeaf', 'url'=>array('index')),
	array('label'=>'Manage PlantLeaf', 'url'=>array('admin')),
);
?>

<h1>Create PlantLeaf</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>