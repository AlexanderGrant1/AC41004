<?php

/**
 * This is the model class for table "potato_Tuber_tutorial".
 *
 * The followings are the available columns in table 'potato_Tuber_tutorial':
 * @property integer $Id
 * @property integer $TuberId
 * @property integer $TutorialId
 *
 * The followings are the available model relations:
 * @property PotatoTuber $tuber
 * @property PotatoTutorial $tutorial
 */
class TuberTutorial extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return TuberTutorial the static model class
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
		return 'potato_Tuber_tutorial';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('TuberId, TutorialId', 'required'),
			array('TuberId, TutorialId', 'numerical', 'integerOnly'=>true),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, TuberId, TutorialId', 'safe', 'on'=>'search'),
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
			'tuber' => array(self::BELONGS_TO, 'Tuber', 'TuberId'),
			'tutorial' => array(self::BELONGS_TO, 'Tutorial', 'TutorialId'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'TuberId' => 'Tuber',
			'TutorialId' => 'Tutorial',
		);
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
		$criteria->compare('TuberId',$this->TuberId);
		$criteria->compare('TutorialId',$this->TutorialId);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}