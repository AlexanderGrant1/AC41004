<?php /* @var $this Controller */ 

	Yii::app()->bootstrap->register();

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main_new.css" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />

	<title><?php echo CHtml::encode($this->pageTitle); ?></title>
</head>

<body>
<div id="wrap">
	<div class="container" id="page">
			<div id="mainmenu">
				<?php $this->widget('bootstrap.widgets.TbNavbar', array(
				    'color' => TbHtml::NAVBAR_COLOR_INVERSE,
				    'brandLabel' => CHtml::encode(Yii::app()->name),
				    'collapse' => true,
				    'items'=>array(
				    			array(
					    			'class' => 'bootstrap.widgets.TbNav',
					    			'items' => array(
									array('label'=>'Home', 'url'=>array('/')),
									array('label'=>'Pests', 'url'=>array('pest/'), 'visible'=>!Yii::app()->user->isGuest),
									array('label'=>'Plants / Leafs', 'url'=>array('/plantleaf/'), 'visible'=>!Yii::app()->user->isGuest),
									array('label'=>'Tubers', 'url'=>array('/tuber/'), 'visible'=>!Yii::app()->user->isGuest),
									array('label'=>'Tutorials', 'url'=>array('/tutorial/'), 'visible'=>!Yii::app()->user->isGuest),
									array('label'=>'Login', 'url'=>array('/site/login'), 'visible'=>Yii::app()->user->isGuest),
									TbHtml::navbarSearchForm('#'),
									array('label'=>'Logout ('.Yii::app()->user->name.')', 'url'=>array('/site/logout'), 'visible'=>!Yii::app()->user->isGuest)
								),
				    		),
						),
				)); ?>
			</div><!-- mainmenu -->
			<?php if(isset($this->breadcrumbs)):?>
				<?php $this->widget('bootstrap.widgets.TbBreadcrumb', array(
					'links'=>$this->breadcrumbs,
				)); ?><!-- breadcrumbs -->
			<?php endif?>


		<?php echo $content; ?>

		<div class="clear"></div>

		<div id="footer">
			Copyright &copy; <?php echo date('Y'); ?> by Team 8.<br/>
			All Rights Reserved.<br/>
			<?php echo Yii::powered(); ?>
		</div><!-- footer -->
	</div><!-- page -->
</div>
</body>
</html>
