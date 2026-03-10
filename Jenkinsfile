pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        // ─────────────────────────────────
        // STAGE 1: BUILD
        // ─────────────────────────────────
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        // ─────────────────────────────────
        // STAGE 2: DEPLOY TO QA
        // ─────────────────────────────────
        stage('Deploy to QA') {
            steps {
                echo "Deploy to QA done"
            }
        }

        // ─────────────────────────────────
        // STAGE 3: REGRESSION TESTS
        // ─────────────────────────────────
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/madhumatiSonnada/OpencartApp2026.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                }
            }
        }

        // ─────────────────────────────────
        // STAGE 4: ALLURE REPORT
        // ─────────────────────────────────
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        // ─────────────────────────────────
        // STAGE 5: HTML REPORT
        // ─────────────────────────────────
        stage('Publish ChainTest HTML Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

    } // stages close

    // ─────────────────────────────────────────
    // POST BUILD ACTIONS
    // ─────────────────────────────────────────
    post {

        success {
            echo '✅ Pipeline PASSED!'
        }

        failure {
            echo '❌ Pipeline FAILED!'
        }

        unstable {
            echo '⚠️ Pipeline UNSTABLE — Some tests failed!'
        }

        always {
            echo '🔄 Cleaning workspace...'
            cleanWs()
        }

    } // post close

} // pipeline close