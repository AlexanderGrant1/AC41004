<?php
/* @var $this TuberController */
/* @var $dataProvider CActiveDataProvider */

$this->breadcrumbs=array(
	'Tubers',
);

$this->menu=array(
	array('label'=>'Create Tuber', 'url'=>array('create')),
	array('label'=>'Manage Tuber', 'url'=>array('admin')),
);
?>

<h1>Tubers</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
