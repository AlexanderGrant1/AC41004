<?php
/* @var $this PlantLeafController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Plant Leaves',
);

$this->menu=array(
	array('label'=>'Create PlantLeaf', 'url'=>array('create')),
	array('label'=>'Manage PlantLeaf', 'url'=>array('admin')),
);
?>

<h1>Plant Leaves</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
