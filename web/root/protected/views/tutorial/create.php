<?php
/* @var $this TutorialController */
/* @var $model Tutorial */

$this->breadcrumbs=array(
	'Tutorials'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Tutorial', 'url'=>array('index')),
);
?>

<h1>Create Tutorial</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>