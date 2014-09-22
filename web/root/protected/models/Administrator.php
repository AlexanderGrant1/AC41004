<?php

/**
 * This is the model class for table "potato_Administrator".
 *
 * The followings are the available columns in table 'potato_Administrator':
 * @property integer $Id
 * @property string $Username
 * @property string $LoginKey
 */
class Administrator extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Administrator the static model class
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
		return 'potato_Administrator';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('Username, LoginKey', 'required'),
			array('Username', 'length', 'max'=>30),
			array('LoginKey', 'length', 'max'=>40),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, Username', 'safe', 'on'=>'search'),
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
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'Username' => 'Username',
			'LoginKey' => 'Login Key',
		);
	}

	/**
	 * Hash the password, so that it is not stored in plain text.
	 */
	public static function hashPassword($password)
	{
		$salt = '@£2@$@NT!£@%£KPml555)()!q523zm54562MO3@3r456uk';

		$hashed = sha1(md5($password.$salt).$salt);

		return $hashed;
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
		$criteria->compare('Username',$this->Username,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}