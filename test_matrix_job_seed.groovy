String folder1 = 'Test1/'
String folder2 = 'Test2/'

def proj_names = [
    folder1 + 'Name1',
    folder2 + 'Name2',
]

matrixJob('SarahTestjobMatrix') {
    wrappers {
        preBuildCleanup()
    }
    triggers {
        upstream(proj_names.join(','), 'SUCCESS')
    }
    steps {
        for (proj_name in proj_names) {
            int i = proj_name.lastIndexOf('/');
            String proj_target = proj_name.substring(i+1);
            step ([$class: 'CopyArtifact',
                projectName: '$proj_name',
                filter: "*.xml,**/.test",
                target: '$proj_target']);
        }
    }
}

