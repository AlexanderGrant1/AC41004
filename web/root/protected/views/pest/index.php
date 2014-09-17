<?php
/* @var $this PestController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Pests',
);

$this->menu=array(
	array('label'=>'Create Pest', 'url'=>array('create')),
	array('label'=>'Manage Pest', 'url'=>array('admin')),
);
?>

<h1>Pests</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
