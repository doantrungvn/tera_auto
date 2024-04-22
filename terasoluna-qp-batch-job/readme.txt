*******************************************************************************
      TERASOLUNA Batch Framework for Java
              �@�\�T���v�� �����菇�ɂ���

      Copyright 2007-2015 NTT DATA Corporation.
*******************************************************************************

��  �T�v�F

  ����readme�́ATERASOLUNA Batch Framework for Java��
  �@�\�T���v���v���W�F�N�g�𓱓�����菇���ł��B
  ���L�菇�ɏ]�����Ƃɂ��ATERASOLUNA�t���[�����[�N�̋@�\�T���v����
  ���s�����������邱�Ƃ��ł��܂��B

��  �O������F

  �J�����ɂ́A���炩���߉��L�̂��̂��p�ӂ���Ă���K�v������܂��B
  �܂����L����Ă���o�[�W���������ɓ���m�F���s�Ȃ��Ă��܂����A
  ���̃o�[�W�����ȊO�̊��œ���𐧌����邱�Ƃ��������̂ł͂���܂���B
  �܂��A�����̃C���X�g�[���y�ѐݒ�̎菇�ɂ��ẮA
  �ʓrWeb��̗��p�K�C�h�����Q�Ƃ��Ă��������B 

  �EJava 2 Runtime Environment Standard Edition 1.7.0
  �EEclipse SDK 3.7.2 + Maven�v���O�C��
  �EPostgreSQL 9.3
    �܂���
  �EOracle 12c
        
��  �v���W�F�N�g�̎��s�F
     
  �@ZIP�t�@�C���̓W�J
    terasoluna-batch-functionsample-(�o�[�W�����ԍ�).zip���uC:\�v�����ɓW�J���܂��B
      �E��uC:\terasoluna-batch-functionsample-(�o�[�W�����ԍ�)\�v
       ���w�肳�ꂽ�f�B���N�g���͌Œ�ł͂Ȃ����߁A�K�X�ǂݑւ��Ď��s���Ă��������B
       �����ł́A�iWindows 7�́jC:\�ɓW�J����Ɖ��肵�A�菇��i�߂܂��B

  �A�f�[�^�x�[�X���̐ݒ�A������(�W���u���s�O�ɕK�����s����)
   ��PostgreSQL�̏ꍇ
    1.�O�����(���ɂ��ύX�\)
      pgAdmin���N�����A�V�����f�[�^�x�[�X���쐬����B
        ���O���functionsampledb
        �I�[�i�[���sample
        �G���R�[�f�B���O���UTF8
        Template����i�Ȃ��j
        �e�[�u����ԥ��pg_default

    2.�usetup_for_PostgreSQL.bat�v�̕ҏW
       �u/sql/postgresql/setup_for_PostgreSQL.bat�v����сu/sql/postgresql/init_job_control.bat�v��
        ���[���̊��ɍ������l�ɏ��������܂��B
        �ڍׂ́u/sql/postgresql/setup_for_PostgreSQL.bat�v���Q�Ƃ��Ă��������B

    3.�e�[�u���̍쐬
       �u/sql/postgresql/setup_for_PostgreSQL.bat�v�����s���܂��B(eclipse������s�s��)

   ��Oracle�̏ꍇ
    1.�O�����(���ɂ��ύX�\)
        �C���X�^���X�����XE
        DB���[�U�[��/�p�X���[�h���sample/sample

    2.�usetup_for_Oracle.bat�v�̕ҏW
        �u/sql/oracle/setup_for_Oracle.bat�v����сu/sql/oracle/init_job_control.bat�v��
        ���[���̊��ɍ������l�ɏ��������܂��B
        �ڍׂ́u/sql/oracle/setup_for_Oracle.bat�v���Q�Ƃ��Ă��������B

    3.�e�[�u���̍쐬
      �u/sql/oracle/setup_for_Oracle.bat�v�����s���܂��B(eclipse������s�s��)
      �uSQL> �v���\�����ꂽ�� exit�Ɠ��͂��ďI�����܂��B

  �BEclipse�ւ̃C���|�[�g
    1.Eclipse��ʂɂāu�t�@�C�����C���|�[�g�v�����s���A
      �uMaven��Existing Maven Projects�v��I�����u���ցv���N���b�N���܂��B
    2.�uRootDirectory:�v�́uBrowse...�v���N���b�N���A
    �@�v���W�F�N�g���e�̃u���E�Y����@�œW�J�����f�B���N�g�����w�肵�܂��B
    3.�u/pom.xml jp.terasoluna.fw:terasoluna-batch-functionsample:(�o�[�W�����ԍ�).jar�v��
    �@�`�F�b�N�������Ă��邱�Ƃ��m�F��A�u�����v���N���b�N���܂��B

  �CJDBC�h���C�o�̐ݒ�
  ���p����DBMS�ɂ��ݒ�菇���قȂ�܂��B
   ��PostgreSQL�̏ꍇ
    1.�u/pom.xml�v�̕ҏW
       Maven�̃Z���g�������|�W�g������JDBC�h���C�o���擾���܂��B
       pom.xml�Ɉȉ��̂悤�ȋL�q���K�v�ɂȂ�܂�(���炩���ߐݒ肳��Ă��܂�)�B

       <!-- JDBC Driver(PostgreSQL) -->
       <dependency>
           <groupId>org.postgresql</groupId>
           <artifactId>postgresql</artifactId>
           <version>9.3-1102-jdbc41</version>
           <scope>runtime</scope>
       </dependency>

       ��<version>�^�O�ɋL�ڂ���o�[�W�����́A���p����PostgreSQL�̃o�[�W�����ɍ��킹�đI�����Ă��������B
         Maven�̃Z���g�������|�W�g���Ɍ��J����Ă���o�[�W�����́A�ȉ���URL���猟�����邱�Ƃ��ł��܂��B
         http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.postgresql%22%20AND%20a%3A%22postgresql%22

   ��Oracle�̏ꍇ
    1.JDBC�h���C�o�̎擾
       �ȉ���URL����JDBC�h���C�o���擾���A�u/scripts/developments�v�t�H���_�ɔz�u���Ă��������B
          http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html

    2.�u/scripts/developments/installojdbc.bat�v�̕ҏW
       �u/scripts/developments/installojdbc.bat�v��FILE_NAME�AGROUP_ID�AARTIFACT_ID�AVERSION�̒l���A
        �ȉ��̂悤�Ɏ����Ŏg�p����JDBC�h���C�o�̒l�ɏ��������Ă��������B

        REM �C���X�g�[������jar�t�@�C���̖��O
        SET FILE_NAME=ojdbc7.jar
        REM �C���X�g�[������jar��groupId (�ύX�s�v)
        SET GROUP_ID=com.oracle
        REM �C���X�g�[������jar��artifactId (�t�@�C�����Ƒ�����)
        SET ARTIFACT_ID=ojdbc7
        REM �C���X�g�[������jar�̃o�[�W����
        SET VERSION=12.1.0.1

        ��bat�t�@�C���ɂ�Oracle Database 12c Release 1��ojdbc7.jar���g�p����ꍇ��
          �ݒ肪���炩���ߋL�ڂ���Ă��܂��B

    3.�u/scripts/developments/installojdbc.bat�v�̎��s
       2.�ŕҏW�����u/scripts/developments/installojdbc.bat�v�����s���܂��B
       �R�}���h�v�����v�g�������オ��A�uBUILD SUCCESS�v�����O�ɏo�͂���Ă��邱�Ƃ��m�F���܂��B

    4.�u/pom.xml�v�̕ҏW
       2.�ŃC���X�g�[������JDBC�h���C�o���擾���邽�߂ɁA
       pom.xml�Ɉȉ��̂悤�ȋL�q���K�v�ɂȂ�܂�(���炩���ߐݒ肪�R�����g�A�E�g����Ă��܂�)�B
       2.�Ŏw�肵��GROUP_ID�AARTIFACT_ID�AVERSION�̊e�l���g�p���܂��B

       <!-- JDBC Driver(Oracle) -->
       <dependency>
           <groupId>com.oracle</groupId> <!-- 2.�Ŏw�肵��GROUP_ID�̒l -->
           <artifactId>ojdbc7</artifactId> <!-- 2.�Ŏw�肵��ARTIFACT_ID�̒l -->
           <version>12.1.0.1</version> <!-- 2.�Ŏw�肵��VERSION�̒l -->
           <scope>runtime</scope>
       </dependency>

  �D���͗p�t�@�C���̔z�u
    �C���|�[�g�����v���W�F�N�g�ɑ��݂���u/input�v�t�H���_�̒��g��C:\tmp\�ɔz�u���܂��B
    
  �EOracle���g�p����ꍇ�́A�ݒ�t�@�C���̏����������s���܂��B(Postgres���g�p����ꍇ�͕s�v)
    1.�f�[�^�x�[�X�ڑ��ݒ�̏�������
    �u/src/main/resources/mybatisAdmin/jdbc.properties�v�����
    �u/src/main/resources/mybatis/jdbc.properties�v�̓��e���A�����ɍ��킹���������܂��B
    
    2.�V�X�e�������p����DAO�̏�������
     �u/src/main/resources/beansAdminDef/AdminDataSource.xml�v��
      �V�X�e�����pDAO��`(PostgreSQL)���R�����g�A�E�g���A�V�X�e�����pDAO��`(Oracle)�̃R�����g�A�E�g���O���܂��B

��  �W���u�̋N��(�J����)�F

    1.�u/scripts/developments/copydependencies.bat�v�����s���A�u/lib�v�f�B���N�g���z����
      ���݂�pom.xml�ňˑ��֌W����`���ꂽjar�����ׂăR�s�[���܂��B
      1�x���s����΁Apom.xml�ɕύX�������Ȃ�����A�Ď��s����K�v�͂���܂���B
    2.�J�����œ���m�F���s���ꍇ�́A�u/scripts/developments/compile.bat�v�����s���A
      �u/lib�v�f�B���N�g���z���� ���݂̃\�[�X�R�[�h�ŃR���p�C�����ꂽ
      �uterasoluna-batch-functionsample-(�o�[�W�����ԍ�).jar�v���R�s�[���܂��B
    3.�u/scripts�v�f�B���N�g���z���̋N���X�N���v�g(B00X00X.bat)�����s���܂��B

��  �W���u�̋N��(�������E���^�p��)�F

    1.�@�\�T���v���v���W�F�N�g�̃��[�g�f�B���N�g���ŁA�umvn package�v�R�}���h�����s���܂��B
    2.�u/target�v�f�B���N�g���Ɂuterasoluna-batch-functionsample-(�o�[�W�����ԍ�)-distribution.zip�v���쐬����܂��B
    3.�uterasoluna-batch-functionsample-(�o�[�W�����ԍ�)-distribution.zip�v���������E���^�p���ֈڑ����A�𓀂��܂��B
    4.�u/scripts�v�f�B���N�g���z���̋N���X�N���v�g(B00X00X.bat)�����s���܂��B

��  ����m�F�p�T���v���̃W���u�ɂ���

    �{�T���v���̃W���u�ꗗ�����L�Ɏ����܂��B

    1. jp.terasoluna.batch.functionsample.b001
      �E�����^�W���u���s�@�\�A�r�W�l�X���W�b�N���s�@�\�A�g�����U�N�V�����Ǘ��@�\�A�f�[�^�x�[�X�A�N�Z�X�@�\�̃T���v��

        ���ׂẴf�[�^��familyName���u��؁v�ɁAfirstName���u���Y�v�ɏ��������鏈�����s���܂��B
        �g�����U�N�V�����Ǘ��@�\�ƃf�[�^�x�[�X�A�N�Z�X�@�\�̃o���G�[�V������4�ɕ������Ă��܂��B
       
        �Ejp.terasoluna.batch.functionsample.b001.b001001
            B001001�F�uscripts/B001001.bat�v����N������
              �g�����U�N�V�����Ǘ�:
                AbstractTransactionBLogic���p�����t���[�����[�N����
                �g�����U�N�V�����Ǘ���C����T���v���ł��B
                �f�[�^�͑S���ꊇ�ŃR�~�b�g����܂��B
              �f�[�^�x�[�X�A�N�Z�X�@�\:
                �o�b�`�X�V��p���Ȃ��T���v���ł��B

        �Ejp.terasoluna.batch.functionsample.b001.b001002
            B001002�F�uscripts/B001002.bat�v����N������
              �g�����U�N�V�����Ǘ�:
                BLogic�C���^�t�F�[�X���p�����r�W�l�X���W�b�N����
                �g�����U�N�V�����̊Ǘ����s���T���v���ł��B
                �f�[�^��10�����ƂɃR�~�b�g����܂��B
              �f�[�^�x�[�X�A�N�Z�X�@�\:
                �o�b�`�X�V��p���Ȃ��T���v���ł��B

        �Ejp.terasoluna.batch.functionsample.b001.b001003
             B001003�F�uscripts/B001003.bat�v����N������
              �g�����U�N�V�����Ǘ�:
                AbstractTransactionBLogic���p�����t���[�����[�N����
                �g�����U�N�V�����Ǘ���C����T���v���ł��B
                �f�[�^�͑S���ꊇ�ŃR�~�b�g����܂��B
              �f�[�^�x�[�X�A�N�Z�X�@�\:
                �o�b�`�X�V��p����T���v���ł��B
                �X�V�n��SQL���܂Ƃ߂Ď��s����ꍇ�A�o�b�`�X�V��p����Ɛ��\�̌��オ�����߂܂��B
                �������̌͊�������邽�߁A�R�~�b�g���ȊO�ɁA
                SqlSession�C���^�t�F�[�X��flushStatements���\�b�h���g�p���A
                10�����ƂɃo�b�`�X�V(�R�~�b�g�͍s��Ȃ�)�����s���Ă��܂��B

        �Ejp.terasoluna.batch.functionsample.b001.b001004
            B001004�F�uscripts/B001004.bat�v����N������
              �g�����U�N�V�����Ǘ�:
                BLogic�C���^�t�F�[�X���p�����r�W�l�X���W�b�N����
                �g�����U�N�V�����̊Ǘ����s���T���v���ł��B
                �f�[�^��10�����ƂɃR�~�b�g����܂��B
              �f�[�^�x�[�X�A�N�Z�X�@�\:
                �o�b�`�X�V��p����T���v���ł��B
                �X�V�n��SQL���܂Ƃ߂Ď��s����ꍇ�A�o�b�`�X�V��p����Ɛ��\�̌��オ�����߂܂��B
                10�����ƂɃR�~�b�g���Ă���A�R�~�b�g�̃^�C�~���O(�R�~�b�g���O)�Ńo�b�`�X�V�����s����邽�߁A
                SqlSession�C���^�t�F�[�X��flushStatements���\�b�h���g�p�����o�b�`�X�V�͎��s���Ă��܂���B

    2. jp.terasoluna.batch.functionsample.b002
      �E�񓯊��^�W���u�̃T���v��

          Employee�e�[�u���̓��e��Employee2�e�[�u���ɃR�s�[���鏈�����s���܂��B

        �Ejp.terasoluna.batch.functionsample.b002.b002001
            B002001BLogic�F�uscripts/B002001_forPostgreSQL.bat�v����N������
              �񓯊��^�W���u�G�O�[�L���[�^�\���N�����ăW���u��񓯊��Ɏ��s����T���v���ł��B

              ��DB��Oracle���g�p���Ă���ꍇ�́uB002001_forOracle.bat�v����N�����Ă��������B

              ���񓯊��^�W���u�G�O�[�L���[�^�[���I������ɂ�
              �uscripts/B002001_TERMINATE.bat�v�����s���Ă��������B

    3. jp.terasoluna.batch.functionsample.b003
      �E��O�n���h�����O�@�\�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b003.b003001
            B003001BLogic�F�uscripts/B003001.bat�v����N������
              �W���u���s���Ƀr�W�l�X���W�b�N�ŗ�O�����������ꍇ�ɁA
              B003001ExceptionHandler�N���X�Ńn���h�����O���s���T���v���ł��B

    4. jp.terasoluna.batch.functionsample.b004
      �E�t�@�C������@�\�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b004.b004001
            B004001BLogic�F�uscripts/B004001.bat�v����N������
             �uC:/tmp/input.csv�v�t�@�C�����uC:/tmp/outputB004001.csv�v��
              �R�s�[����T���v���ł��B
    
    5. jp.terasoluna.batch.functionsample.b005
      �E���b�Z�[�W�Ǘ��@�\�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b005.b005001
            B005001BLogic�F�uscripts/B005001.bat�v����N������
             �uapplication-messages.properties�v�ɒ�`�������b�Z�[�W�𗘗p�������O�o�͂��s���T���v���ł��B

    6. jp.terasoluna.batch.functionsample.b006
      �E�{�o�[�W�����ł́A�u�o�b�`�X�V�œK���@�\�v�͒񋟂��Ă��Ȃ����߁A�T���v���͂���܂���B
        �f�[�^�x�[�X�A�N�Z�X�@�\�̃o�b�`�X�V��p����T���v��(B001003�AB001004)�𗘗p���Ă��������B

    7. jp.terasoluna.batch.functionsample.b007
      �E���̓f�[�^�擾�@�\�A�f�[�^�x�[�X�A�N�Z�X�@�\�A�t�@�C���A�N�Z�X�@�\�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b007.b007001
            B007001BLogic�F�uscripts/B007001.bat�v����N������
              ���̓f�[�^�擾�@�\�𗘗p���ăt�@�C���uC:/tmp/input.csv�v�̓��e��ǂݍ��݁A
              �f�[�^�x�[�X�A�N�Z�X�@�\�𗘗p���āuEmployee�e�[�u���v�Ƀf�[�^��}������T���v���ł��B

        �Ejp.terasoluna.batch.functionsample.b007.b007002
            B007002BLogic�F�uscripts/B007002.bat�v����N������
              ���̓f�[�^�擾�@�\�𗘗p���ăf�[�^�x�[�X�uEmployee�e�[�u���v�̓��e��ǂݍ��݁A
              �t�@�C���A�N�Z�X�@�\�𗘗p���āuC:/tmp/outputB007002.csv�v�Ƀf�[�^��}������T���v���ł��B

    8. jp.terasoluna.batch.functionsample.b008
      �E�R���g���[���u���C�N�@�\�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b008.b008001
            B008001BLogic�F�uscripts/B008001.bat�v����N������
              �s���{�����Ƃ̎s�撬���A���悪�L�ڂ���Ă���uC:/tmp/KEN_ALL.csv�v��ǂݍ��݁A
              �s���{���P�ʂŁuZIP_CODE�v�e�[�u���Ƀf�[�^��}������T���v���ł��B
              ���̍ۂɁA�s���{�����Ƃ̎s�撬�����A���搔�����O�ɏo�͂��܂��B

    9. jp.terasoluna.batch.functionsample.b009
      �E���̓f�[�^�擾�@�\�g�p���̓��̓`�F�b�N�@�\�A��O�n���h�����O�̃T���v��

        �Ejp.terasoluna.batch.functionsample.b009.b009001
            B009001BLogic�F�uscripts/B009001.bat�v����N������
              �uC:/tmp/inputB009001.csv�v��ǂݍ��݁A�uC:/tmp/outputB009001.csv�v�ɏo�͂��܂��B
              ���̎��ABeanValidation�𗘗p�������̓`�F�b�N�����s����A
              2,11,16���ڂ̃f�[�^�œ��̓`�F�b�N�G���[���������܂��B
              ���̃T���v���ł́A�g�����̓`�F�b�N�G���[�n���h�����O�N���X��p�ӂ��A
              Status�uSKIP�v��ԋp���Ă��邽�߁A2,11,16���ڂ̃f�[�^�̓t�@�C���ɏo�͂���܂���B
          
        �Ejp.terasoluna.batch.functionsample.b009.b009002
            B009002BLogic�F�uscripts/B009002.bat�v����N������
              Employee3�e�[�u����ǂݍ��݁AEmployee2�e�[�u���ɃR�s�[���܂��B
              ���̎��ABeanValidation�𗘗p�������̓`�F�b�N�����s����A
              2,7,12���ڂ̃f�[�^�œ��̓`�F�b�N�G���[���������܂��B
              ���̃T���v���ł́A���̓f�[�^�擾�@�\�g�p���̊g����O�n���h�����O�N���X��p�ӂ��A
              Status�uEND�v��ԋp���Ă��邽�߁A2���ڂ̃f�[�^�œ��̓f�[�^�̎擾����~���܂��B
              ���̂��߁AEmployee�e�[�u���ɂ�1���̂݃f�[�^���R�s�[����܂��B

���t�@�C�����o�͂���W���u�Ɋւ��āA�t�@�C���̍폜�����͓��ɋL�q���Ă��炸�A
  �t�@�C���o�͎��ɂ͏㏑���ƂȂ�悤�ݒ肵�Ă��܂��B

-------------------------------------------------------------------------------
Copyright 2007-2015 NTT DATA Corporation.
