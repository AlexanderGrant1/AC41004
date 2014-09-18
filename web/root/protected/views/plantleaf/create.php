<?php
/* @var $this PlantLeafController */
/* @var $model PlantLeaf */

$this->breadcrumbs=array(
	'Plant Leaves'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List PlantLeaf', 'url'=>array('index')),
);
?>

<h1>Create PlantLeaf</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>