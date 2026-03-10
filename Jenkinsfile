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
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
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
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
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
    // POST BUILD — SUCCESS & FAILURE
    // ─────────────────────────────────────────
    post {

        success {
            echo '✅ Pipeline PASSED!'
            mail(
                to: 'madhumati217@gmail.com',
                subject: "✅ BUILD PASSED — ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    Hi Team,

                    ✅ Build PASSED Successfully!

                    Job Name    : ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Branch      : ${env.BRANCH_NAME}
                    Duration    : ${currentBuild.durationString}
                    Build URL   : ${env.BUILD_URL}
                    Allure Report: ${env.BUILD_URL}allure/
                    HTML Report  : ${env.BUILD_URL}HTML_Regression_ChainTest_Report/

                    Regards,
                    Jenkins CI
                """
            )
        }

        failure {
            echo '❌ Pipeline FAILED!'
            mail(
                to: 'madhumati217@gmail.com',
                subject: "❌ BUILD FAILED — ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    Hi Team,

                    ❌ Build FAILED — Please investigate!

                    Job Name    : ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Branch      : ${env.BRANCH_NAME}
                    Duration    : ${currentBuild.durationString}
                    Build URL   : ${env.BUILD_URL}
                    Console Log : ${env.BUILD_URL}console

                    Please check the console log for details.

                    Regards,
                    Jenkins CI
                """
            )
        }

        unstable {
            echo '⚠️ Pipeline UNSTABLE — Some tests failed!'
            mail(
                to: 'madhumati217@gmail.com',
                subject: "⚠️ BUILD UNSTABLE — ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    Hi Team,

                    ⚠️ Build is UNSTABLE — Some tests failed!

                    Job Name    : ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Branch      : ${env.BRANCH_NAME}
                    Duration    : ${currentBuild.durationString}
                    Build URL   : ${env.BUILD_URL}
                    Test Report : ${env.BUILD_URL}testReport/

                    Please review failing tests.

                    Regards,
                    Jenkins CI
                """
            )
        }

        always {
            echo '🔄 Pipeline finished — cleaning workspace'
            cleanWs()  // Clean workspace after every build
        }

    } // post close

} // pipeline close