import groovy.json.JsonSlurper
println aem_opencloud_manager_version
def test_pipeline_name = aem_opencloud_manager_version + "/utilities/custom/" 
def json_content = '{"env_list": ["dev1", "dev2", "dev3", "dev4", "dev5"]}'
def slurper = new groovy.json.JsonSlurper()
def env_data = slurper.parseText(json_content)
env_data.each {
  target_key, target_value ->
  target_value.each {
    println "--> ${it}"
    new_pipeline_name = test_pipeline_name + "${it}"    
    pipelineJob(new_pipeline_name)
    {
      environmentVariables {
          env('AWS_REGION', "ap-southeast-2")
      }
      parameters{
        stringParam('ENV_NAME', '', 'Environment Name. Allowed values: dev1, dev2, dev3, dev4, dev5, pdev, ptest, training, stest, vtest')
        stringParam('STACK_PREFIX', 'test-prefix', 'Enter the aws cloudformation stack prefix if you know')
      }
      definition{
        cpsScm {  
          scriptPath('pipeline/some_Jenkinsfile')
          scm {
            git {
              branch('refs/heads/master')
              remote {
                name('pipeline')
                url('repo_url')
                credentials('1')
              }
              extensions {
                cleanAfterCheckout()
                disableRemotePoll()
                cloneOptions {
                  shallow(shallow = true)
                }
              }
            }
          }
        }
      }
    }
  }
}

