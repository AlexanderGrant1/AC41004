<?php

/**
 * This is the model class for table "potato_Tuber".
 *
 * The followings are the available columns in table 'potato_Tuber':
 * @property integer $Id
 * @property string $Name
 * @property string $Description
 *
 * The followings are the available model relations:
 * @property PotatoTuberPhoto[] $potatoTuberPhotos
 */
class Tuber extends CActiveRecord
{
	public $image;		// Used for uploading file.
	public $imageName;  // Used as a tmp field to store image name.


	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Tuber the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'potato_Tuber';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('Name, Description', 'required'),
			array('Name', 'length', 'max'=>50),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, Name, Description', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'images' => array(self::HAS_MANY, 'TuberPhoto', 'TuberId'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'Name' => 'Name',
			'Description' => 'Description',
		);
	}

	/** 
	 * Does some extra work after being saved.
	 *
	 */
	public function afterSave()
	{
		if(isset($this->image))
		{
			// The user is uploading an image.
			do 
			{
				// Generate a name for this image.
				$this->imageName = md5(time());
			}while(file_exists(Yii::app()->params['projectPath'].Yii::app()->params['imagePath'].$this->imageName.'.jpg'));

			$this->createImage();
		}

		return true;
	}

	/**
	 * Create images for this article.
	 */
	public function createImage()
	{
		$thumb = Yii::app()->phpThumb->create($this->image->getTempName());

		if($value['resizeMethod'] == 'resize')
		{
			$thumb->resize(600,600);
		}

		$path = Yii::app()->params['projectPath'].Yii::app()->params['imagePath'];
	
		$thumb->save($path.$this->imageName.'.jpg','jpg');

		// Add this image to the db.
		$pic = new Photo;
		$pic->Name = $this->imageName.'.jpg';

		if($pic->save())
		{
			// Create new linker.
			$picLinker = new TuberPhoto;
			$picLinker->TuberId = $this->Id;
			$picLinker->PhotoId = $pic->Id;

			$picLinker->save();
		}
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('Id',$this->Id);
		$criteria->compare('Name',$this->Name,true);
		$criteria->compare('Description',$this->Description,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}